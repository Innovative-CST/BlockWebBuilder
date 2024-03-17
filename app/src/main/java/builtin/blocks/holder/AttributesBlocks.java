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
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class AttributesBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#4759B8");
    blocksHolder.setName("Attributes");
    blocksHolder.setTags(new String[] {BlocksHolder.Tags.HTML});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getIdBlock());
    blocksList.add(getClassBlock());

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static Block getIdBlock() {
    Block idBlock = new Block();
    idBlock.setColor("#4759B8");
    idBlock.setBlockType(Block.BlockType.sideAttachableBlock);
    idBlock.setName("id");

    StringBuilder blockRawCode = new StringBuilder();
    blockRawCode.append("id=\"");
    blockRawCode.append(CodeReplacer.getReplacer("id"));
    blockRawCode.append("\"");

    idBlock.setRawCode(blockRawCode.toString());
    idBlock.setTags(new String[] {Block.Tags.HTML});
    idBlock.setEnableSideAttachableBlock(true);

    ArrayList<BlockContent> idBlockContentList = new ArrayList<BlockContent>();

    BlockContent idBlockContent1 = new BlockContent();
    idBlockContent1.setText("id");
    idBlockContentList.add(idBlockContent1);

    SourceContent idBlockContent2 = new SourceContent();
    idBlockContent2.setId("id");
    idBlockContentList.add(idBlockContent2);

    idBlock.setBlockContent(idBlockContentList);

    return idBlock;
  }

  public static Block getClassBlock() {
    Block classBlock = new Block();
    classBlock.setColor("#4759B8");
    classBlock.setBlockType(Block.BlockType.sideAttachableBlock);
    classBlock.setName("class");

    StringBuilder blockRawCode = new StringBuilder();
    blockRawCode.append("class=\"");
    blockRawCode.append(CodeReplacer.getReplacer("class"));
    blockRawCode.append("\"");

    classBlock.setRawCode(blockRawCode.toString());
    classBlock.setTags(new String[] {Block.Tags.HTML});
    classBlock.setEnableSideAttachableBlock(true);

    ArrayList<BlockContent> classBlockContentList = new ArrayList<BlockContent>();

    BlockContent classBlockContent1 = new BlockContent();
    classBlockContent1.setText("class");
    classBlockContentList.add(classBlockContent1);

    SourceContent classBlockContent2 = new SourceContent();
    classBlockContent2.setId("class");
    classBlockContentList.add(classBlockContent2);

    classBlock.setBlockContent(classBlockContentList);

    return classBlock;
  }
}
