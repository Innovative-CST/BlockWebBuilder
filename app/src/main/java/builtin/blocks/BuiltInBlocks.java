package builtin.blocks;

import android.graphics.Color;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.BlocksHolder;
import com.dragon.ide.objects.ComplexBlockContent;
import com.dragon.ide.objects.blockcontent.SourceContent;
import java.util.ArrayList;

public class BuiltInBlocks {
  public static ArrayList<BlocksHolder> getBuiltInBlocksHolder() {
    ArrayList<BlocksHolder> blocksHolder = new ArrayList<BlocksHolder>();
    BlocksHolder holder1 = new BlocksHolder();
    holder1.setColor("#009900");
    holder1.setName("Operators");

    ArrayList<Block> blockList = new ArrayList<Block>();

    Block blockInHolder1 = new Block();
    blockInHolder1.setColor("#009900");
    blockInHolder1.setBlockType(Block.BlockType.defaultBlock);
    blockInHolder1.setName("addSource");
    blockInHolder1.setRawCode("%%%% DragonIDE param1 %%%%");

    ArrayList<Object> block1ContentList = new ArrayList<Object>();

    BlockContent block1Content1 = new BlockContent();
    block1Content1.setText("add source");
    block1ContentList.add(block1Content1);

    SourceContent block1Content2 = new SourceContent();
    block1Content2.setId("param1");
    block1ContentList.add(block1Content2);

    blockInHolder1.setBlockContent(block1ContentList);

    blockList.add(blockInHolder1);

    holder1.setBlocks(blockList);

    blocksHolder.add(holder1);

    return blocksHolder;
  }
}
