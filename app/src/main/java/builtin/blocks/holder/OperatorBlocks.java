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

public class OperatorBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#5AC417");
    blocksHolder.setName("Operator");
    blocksHolder.setTags(new String[] {BlocksHolder.Tags.HTML, BlocksHolder.Tags.CSS, BlocksHolder.Tags.JavaScript});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getTrueBlock());
    blocksList.add(getfalseBlock());
    blocksList.add(getAddSourceBlock());

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static Block getAddSourceBlock() {
    Block addSource = new Block();
    addSource.setColor("#5AC417");
    addSource.setBlockType(Block.BlockType.defaultBlock);
    addSource.setName("addSource");
    addSource.setRawCode(CodeReplacer.getReplacer("parameter"));
    addSource.setTags(new String[] {Block.Tags.HTML, Block.Tags.CSS, Block.Tags.JavaScript});

    ArrayList<BlockContent> addSourceContentList = new ArrayList<BlockContent>();

    BlockContent addSourceBlockContent1 = new BlockContent();
    addSourceBlockContent1.setText("add source directly");
    addSourceContentList.add(addSourceBlockContent1);

    SourceContent addSourceBlockContent2 = new SourceContent();
    addSourceBlockContent2.setId("parameter");
    addSourceContentList.add(addSourceBlockContent2);

    addSource.setBlockContent(addSourceContentList);

    return addSource;
  }

  public static Block getTrueBlock() {
    Block trueBlock = new Block();
    trueBlock.setColor("#5AC417");
    trueBlock.setBlockType(Block.BlockType.returnWithTypeBoolean);
    trueBlock.setName("true");
    trueBlock.setRawCode("true");
    trueBlock.setReturns("boolean");
    trueBlock.setTags(new String[] {Block.Tags.JavaScript});
    ArrayList<BlockContent> trueBlockContentList = new ArrayList<BlockContent>();
    BlockContent trueBlockText = new BlockContent();
    trueBlockText.setText("true");
    trueBlockContentList.add(trueBlockText);
    trueBlock.setBlockContent(trueBlockContentList);

    return trueBlock;
  }

  public static Block getfalseBlock() {
    Block falseBlock = new Block();
    falseBlock.setColor("#5AC417");
    falseBlock.setBlockType(Block.BlockType.returnWithTypeBoolean);
    falseBlock.setName("false");
    falseBlock.setRawCode("false");
    falseBlock.setReturns("boolean");
    falseBlock.setTags(new String[] {Block.Tags.JavaScript});
    ArrayList<BlockContent> falseBlockContentList = new ArrayList<BlockContent>();
    BlockContent falseBlockText = new BlockContent();
    falseBlockText.setText("false");
    falseBlockContentList.add(falseBlockText);
    falseBlock.setBlockContent(falseBlockContentList);

    return falseBlock;
  }
}
