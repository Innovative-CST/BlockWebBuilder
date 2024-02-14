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
