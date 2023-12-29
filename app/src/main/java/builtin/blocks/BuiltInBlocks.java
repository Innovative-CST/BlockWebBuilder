package builtin.blocks;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;
import builtin.blocks.holder.OperatorBlocks;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.BlocksHolder;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.ComplexBlockContent;
import com.dragon.ide.objects.blockcontent.BooleanContent;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.utils.CodeReplacer;
import editor.tsd.tools.Language;
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
    blockInHolder2.setRawCode("I am block code DevKumar DragonIDE parameter DevKumar ");
    blockInHolder2.setTags(new String[] {"developer", "developerOnly"});

    ArrayList<BlockContent> block2ContentList = new ArrayList<BlockContent>();

    BlockContent block2Content1 = new BlockContent();
    block2Content1.setText("Test block code");
    block2ContentList.add(block2Content1);

    SourceContent block2Content2 = new SourceContent();
    block2Content2.setId("parameter");
    block2ContentList.add(block2Content2);

    blockInHolder2.setBlockContent(block2ContentList);

    Block blockInHolder4 = new Block();
    blockInHolder4.setColor("#009900");
    blockInHolder4.setBlockType(Block.BlockType.returnWithTypeBoolean);
    blockInHolder4.setName("true");
    blockInHolder4.setRawCode("true");
    blockInHolder4.setReturns("boolean");
    blockInHolder4.setTags(new String[] {"developer", "developerOnly"});

    ArrayList<BlockContent> block4ContentList = new ArrayList<BlockContent>();

    BlockContent block4Content1 = new BlockContent();
    block4Content1.setText("true");
    block4ContentList.add(block4Content1);

    blockInHolder4.setBlockContent(block4ContentList);

    Block blockInHolder5 = new Block();
    blockInHolder5.setColor("#009900");
    blockInHolder5.setBlockType(Block.BlockType.returnWithTypeBoolean);
    blockInHolder5.setName("false");
    blockInHolder5.setRawCode("false");
    blockInHolder5.setReturns("boolean");
    blockInHolder5.setTags(new String[] {"developer", "developerOnly"});

    ArrayList<BlockContent> block5ContentList = new ArrayList<BlockContent>();

    BlockContent block5Content1 = new BlockContent();
    block5Content1.setText("false");
    block5ContentList.add(block5Content1);

    blockInHolder5.setBlockContent(block5ContentList);

    Block blockInHolder6 = new Block();
    blockInHolder6.setColor("#ff0000");
    blockInHolder6.setBlockType(Block.BlockType.defaultBlock);
    blockInHolder6.setName("Test");
    blockInHolder6.setEnableSideAttachableBlock(true);
    blockInHolder6.setRawCode("I am block code DevKumar DragonIDE parameter DevKumar ");
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
    blockList.add(blockInHolder4);
    blockList.add(blockInHolder5);
    blockList.add(blockInHolder6);

    holder1.setBlocks(blockList);

    blocksHolder.add(holder1);
    blocksHolder.add(OperatorBlocks.getBlockHolder());

    return blocksHolder;
  }
}
