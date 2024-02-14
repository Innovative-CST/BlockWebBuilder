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
