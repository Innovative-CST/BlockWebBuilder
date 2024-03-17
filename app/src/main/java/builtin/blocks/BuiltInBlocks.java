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

package builtin.blocks;

import builtin.blocks.holder.AttributesBlocks;
import builtin.blocks.holder.ButtonBlocks;
import builtin.blocks.holder.ControlBlocks;
import builtin.blocks.holder.HtmlHeadingBlocks;
import builtin.blocks.holder.OperatorBlocks;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlockContent;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.core.blockcontent.NumberContent;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import java.util.ArrayList;

public class BuiltInBlocks {
  public static ArrayList<BlocksHolder> getBuiltInBlocksHolder() {
    ArrayList<BlocksHolder> blocksHolder = new ArrayList<BlocksHolder>();
    BlocksHolder holder1 = new BlocksHolder();
    holder1.setColor("#009900");
    holder1.setName("Developer tools");
    holder1.setTags(new String[] {BlocksHolder.Tags.Developer, BlocksHolder.Tags.DeveloperOnly});

    ArrayList<Block> blockList = new ArrayList<Block>();

    Block blockInHolder1 = new Block();
    blockInHolder1.setColor("#0033ff");
    blockInHolder1.setBlockType(Block.BlockType.returnWithTypeInteger);
    blockInHolder1.setName("45");
    blockInHolder1.setEnableSideAttachableBlock(true);
    blockInHolder1.setReturns("int");
    blockInHolder1.setRawCode("45 -> " + CodeReplacer.getReplacer("parameter"));
    blockInHolder1.setTags(new String[] {Block.Tags.Developer, Block.Tags.DeveloperOnly});

    ArrayList<BlockContent> block1ContentList = new ArrayList<BlockContent>();

    BlockContent block1Content1 = new BlockContent();
    block1Content1.setText("45");
    block1ContentList.add(block1Content1);

    NumberContent block1Content2 = new NumberContent();
    block1Content2.setId("parameter");
    block1ContentList.add(block1Content2);

    blockInHolder1.setBlockContent(block1ContentList);

    Block blockInHolder2 = new Block();
    blockInHolder2.setColor("#ff0000");
    blockInHolder2.setBlockType(Block.BlockType.defaultBlock);
    blockInHolder2.setName("Test");
    blockInHolder2.setEnableSideAttachableBlock(true);
    blockInHolder2.setRawCode("I am block code " + CodeReplacer.getReplacer("parameter"));
    blockInHolder2.setTags(new String[] {Block.Tags.Developer, Block.Tags.DeveloperOnly});

    ArrayList<BlockContent> block2ContentList = new ArrayList<BlockContent>();

    BlockContent block2Content1 = new BlockContent();
    block2Content1.setText("Test block code");
    block2ContentList.add(block2Content1);

    NumberContent block2Content2 = new NumberContent();
    block2Content2.setId("parameter");
    block2ContentList.add(block2Content2);

    blockInHolder2.setBlockContent(block2ContentList);

    Block blockInHolder6 = new Block();
    blockInHolder6.setColor("#ff0000");
    blockInHolder6.setBlockType(Block.BlockType.sideAttachableBlock);
    blockInHolder6.setName("Test");
    blockInHolder6.setEnableSideAttachableBlock(true);
    blockInHolder6.setRawCode("I am block code " + CodeReplacer.getReplacer("parameter"));
    blockInHolder6.setTags(new String[] {Block.Tags.Developer, Block.Tags.DeveloperOnly});

    ArrayList<BlockContent> block6ContentList = new ArrayList<BlockContent>();

    BlockContent block6Content1 = new BlockContent();
    block6Content1.setText("Test block code");
    block6ContentList.add(block6Content1);

    NumberContent block6Content2 = new NumberContent();
    block6Content2.setId("parameter");
    block6ContentList.add(block6Content2);

    blockInHolder6.setBlockContent(block6ContentList);

    blockList.add(blockInHolder1);
    blockList.add(blockInHolder2);
    blockList.add(blockInHolder6);

    holder1.setBlocks(blockList);

    blocksHolder.add(holder1);
    blocksHolder.add(ControlBlocks.getBlockHolder());
    blocksHolder.add(OperatorBlocks.getBlockHolder());
    blocksHolder.add(AttributesBlocks.getBlockHolder());
    blocksHolder.add(ButtonBlocks.getBlockHolder());
    blocksHolder.add(HtmlHeadingBlocks.getBlockHolder());

    return blocksHolder;
  }
}
