package builtin.blocks;

import builtin.blocks.holder.ControlBlocks;
import builtin.blocks.holder.OperatorBlocks;
import com.block.web.builder.objects.Block;
import com.block.web.builder.objects.BlockContent;
import com.block.web.builder.objects.BlocksHolder;
import com.block.web.builder.objects.blockcontent.SourceContent;
import com.block.web.builder.utils.CodeReplacer;
import java.util.ArrayList;

public class BuiltInBlocks {
  public static ArrayList<BlocksHolder> getBuiltInBlocksHolder() {
    ArrayList<BlocksHolder> blocksHolder = new ArrayList<BlocksHolder>();
    BlocksHolder holder1 = new BlocksHolder();
    holder1.setColor("#009900");
    holder1.setName("Developer tools");
    holder1.setTags(new String[] {"developer", "developerOnly"});

    ArrayList<Block> blockList = new ArrayList<Block>();

    Block blockInHolder2 = new Block();
    blockInHolder2.setColor("#ff0000");
    blockInHolder2.setBlockType(Block.BlockType.sideAttachableBlock);
    blockInHolder2.setName("Test");
    blockInHolder2.setEnableSideAttachableBlock(true);
    blockInHolder2.setRawCode("I am block code " + CodeReplacer.getReplacer("parameter"));
    blockInHolder2.setTags(new String[] {"developer", "developerOnly"});

    ArrayList<BlockContent> block2ContentList = new ArrayList<BlockContent>();

    BlockContent block2Content1 = new BlockContent();
    block2Content1.setText("Test block code");
    block2ContentList.add(block2Content1);

    SourceContent block2Content2 = new SourceContent();
    block2Content2.setId("parameter");
    block2ContentList.add(block2Content2);

    blockInHolder2.setBlockContent(block2ContentList);

    Block blockInHolder6 = new Block();
    blockInHolder6.setColor("#ff0000");
    blockInHolder6.setBlockType(Block.BlockType.defaultBlock);
    blockInHolder6.setName("Test");
    blockInHolder6.setEnableSideAttachableBlock(true);
    blockInHolder6.setRawCode("I am block code " + CodeReplacer.getReplacer("parameter"));
    blockInHolder6.setTags(new String[] {"developer", "developerOnly"});

    ArrayList<BlockContent> block6ContentList = new ArrayList<BlockContent>();

    BlockContent block6Content1 = new BlockContent();
    block6Content1.setText("Test block code");
    block6ContentList.add(block6Content1);

    SourceContent block6Content2 = new SourceContent();
    block6Content2.setId("parameter");
    block6ContentList.add(block6Content2);

    blockInHolder6.setBlockContent(block6ContentList);

    blockList.add(blockInHolder2);
    blockList.add(blockInHolder6);

    holder1.setBlocks(blockList);

    blocksHolder.add(holder1);
    blocksHolder.add(ControlBlocks.getBlockHolder());
    blocksHolder.add(OperatorBlocks.getBlockHolder());

    return blocksHolder;
  }
}
