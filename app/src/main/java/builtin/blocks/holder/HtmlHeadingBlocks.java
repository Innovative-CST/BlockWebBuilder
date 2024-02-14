/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
 */

package builtin.blocks.holder;

import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlockContent;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class HtmlHeadingBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#007BFF");
    blocksHolder.setName("Headings");
    blocksHolder.setTags(new String[] {BlocksHolder.Tags.HTML});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getHeadingBlock("h1", "Heading 1"));
    blocksList.add(getHeadingBlock("h2", "Heading 2"));
    blocksList.add(getHeadingBlock("h3", "Heading 3"));
    blocksList.add(getHeadingBlock("h4", "Heading 4"));
    blocksList.add(getHeadingBlock("h5", "Heading 5"));
    blocksList.add(getHeadingBlock("h6", "Heading 6"));

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static Block getHeadingBlock(String tagName, String blockName) {
    Block headingBlock = new Block();
    headingBlock.setColor("#007BFF");
    headingBlock.setBlockType(Block.BlockType.defaultBlock);
    headingBlock.setName(blockName);

    StringBuilder blockRawCode = new StringBuilder();
    blockRawCode.append("<").append(tagName);
    blockRawCode.append(CodeReplacer.getReplacer("attachementBlock"));
    blockRawCode.append(">");
    blockRawCode.append(CodeReplacer.getReplacer("headingText"));
    blockRawCode.append("</").append(tagName).append(">");

    headingBlock.setRawCode(blockRawCode.toString());
    headingBlock.setTags(new String[] {Block.Tags.HTML});
    headingBlock.setEnableSideAttachableBlock(true);

    ArrayList<BlockContent> headingBlockContentList = new ArrayList<BlockContent>();

    BlockContent headingBlockContent = new BlockContent();
    headingBlockContent.setText(blockName);
    headingBlockContentList.add(headingBlockContent);

    SourceContent headingBlockContent2 = new SourceContent();
    headingBlockContent2.setId("headingText");
    headingBlockContentList.add(headingBlockContent2);

    headingBlock.setBlockContent(headingBlockContentList);

    return headingBlock;
  }
}
