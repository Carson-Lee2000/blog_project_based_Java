package com.lks.blog.blog_project.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    private static final String REPLACEMENT = "*";

    // 根节点初始化
    private TrieNode rootNode = new TrieNode();

    /**
     * 敏感词前缀树初始化
     */
    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 添加敏感词到前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            logger.error("加载敏感词文件失败！" + e.getMessage());
        }
    }

    // 将一个敏感词添加到前缀树中
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            // 指针指向子节点，进入下一次循环
            tempNode = subNode;

            // 敏感词最后一个字符标志
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    // 返回敏感词过滤后的字符串
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;

        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号，例如“赌00博”也会被过滤
            if (isSymbol(c)) {
                // 如果指针1处于根节点，将此符号计入结果，让指针2向下走一步
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // 以begin指向的字符开头的不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                begin++;
                position = begin;
                // 重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // begin - position 替换
                // sb.append(REPLACEMENT);
                for (int i = begin; i <= position; i++) {
                    sb.append(REPLACEMENT);
                }
                // 进入下一个位置
                position++;
                begin = position;
                tempNode = rootNode;
            } else {
                position++;
            }
        }

        // 将最后一批字符计入
        sb.append(text.substring(begin));
        return sb.toString();
    }

    // 判断是否是符号
    private boolean isSymbol(Character c) {
        // 0x2E80-0x9FFF是东亚文字
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // 前缀树数据结构定义
    private class TrieNode {

        // 关键词结束标识
        private boolean isKeywordEnd = false;

        // 子节点，key是下级字符，value是下级子节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点的方法
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }


}
