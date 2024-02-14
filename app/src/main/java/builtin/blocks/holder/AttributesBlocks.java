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
