/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
 */

package builtin.blocks.holder;

import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlockContent;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.core.ComplexBlock;
import com.block.web.builder.core.blockcontent.BooleanContent;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class ControlBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#E1A92A");
    blocksHolder.setName("Control");
    blocksHolder.setTags(new String[] {BlocksHolder.Tags.JavaScript});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getIfBlock());
    blocksList.add(getIncrementalForLoopBlock());
    blocksList.add(getIncrementalForLoopBlockWithVariableValueProvider());
    blocksList.add(getDecrementalForLoopBlock());
    blocksList.add(getDecrementalForLoopBlockWithVariableValueProvider());

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static ComplexBlock getIfBlock() {
    ComplexBlock ifBlock = new ComplexBlock();
    ifBlock.setColor("#E1A92A");
    ifBlock.setBlockType(Block.BlockType.complexBlock);
    ifBlock.setName("if");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("if (");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append(") {\n\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    ifBlock.setRawCode(ifBlockStringBuilder.toString());

    ifBlock.setTags(new String[] {Block.Tags.JavaScript});

    ArrayList<BlockContent> ifBlockContentList = new ArrayList<BlockContent>();

    BlockContent ifBlockBlockContent1 = new BlockContent();
    ifBlockBlockContent1.setText("if");
    ifBlockContentList.add(ifBlockBlockContent1);

    BooleanContent ifBlockBlockContent2 = new BooleanContent();
    ifBlockBlockContent2.setId("condition");
    ifBlockContentList.add(ifBlockBlockContent2);

    BlockContent ifBlockContent3 = new BlockContent();
    ifBlockContent3.setText("then");
    ifBlockContentList.add(ifBlockContent3);

    ifBlock.setBlockContent(ifBlockContentList);

    return ifBlock;
  }

  public static ComplexBlock getIncrementalForLoopBlock() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = 0; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("++) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Block.Tags.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("++");
    forBlockContentList.add(forLoopBlockContent6);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }

  public static ComplexBlock getIncrementalForLoopBlockWithVariableValueProvider() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variableValue"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("++) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Block.Tags.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("value");
    forBlockContentList.add(forLoopBlockContent6);

    SourceContent forLoopBlockContent7 = new SourceContent();
    forLoopBlockContent7.setId("variableValue");
    forBlockContentList.add(forLoopBlockContent7);

    BlockContent forLoopBlockContent8 = new BlockContent();
    forLoopBlockContent8.setText("++");
    forBlockContentList.add(forLoopBlockContent8);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }

  public static ComplexBlock getDecrementalForLoopBlock() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = 0; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("--) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Block.Tags.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("--");
    forBlockContentList.add(forLoopBlockContent6);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }

  public static ComplexBlock getDecrementalForLoopBlockWithVariableValueProvider() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variableValue"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("--) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Block.Tags.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("value");
    forBlockContentList.add(forLoopBlockContent6);

    SourceContent forLoopBlockContent7 = new SourceContent();
    forLoopBlockContent7.setId("variableValue");
    forBlockContentList.add(forLoopBlockContent7);

    BlockContent forLoopBlockContent8 = new BlockContent();
    forLoopBlockContent8.setText("--");
    forBlockContentList.add(forLoopBlockContent8);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }
}
