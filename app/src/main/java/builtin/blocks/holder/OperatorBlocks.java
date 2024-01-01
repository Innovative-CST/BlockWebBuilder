package builtin.blocks.holder;

import com.block.web.builder.objects.Block;
import com.block.web.builder.objects.BlockContent;
import com.block.web.builder.objects.BlocksHolder;
import com.block.web.builder.objects.ComplexBlock;
import com.block.web.builder.objects.blockcontent.BooleanContent;
import com.block.web.builder.objects.blockcontent.SourceContent;
import com.block.web.builder.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class OperatorBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#5AC417");
    blocksHolder.setName("Operator");
    blocksHolder.setTags(new String[] {Language.HTML, Language.CSS, Language.JavaScript});

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
    addSource.setTags(new String[] {Language.HTML, Language.CSS, Language.JavaScript});

    ArrayList<BlockContent> addSourceContentList = new ArrayList<BlockContent>();

    BlockContent addSourceBlockContent1 = new BlockContent();
    addSourceBlockContent1.setText("add source");
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
    trueBlock.setTags(new String[] {Language.JavaScript});
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
    falseBlock.setTags(new String[] {Language.JavaScript});
    ArrayList<BlockContent> falseBlockContentList = new ArrayList<BlockContent>();
    BlockContent falseBlockText = new BlockContent();
    falseBlockText.setText("false");
    falseBlockContentList.add(falseBlockText);
    falseBlock.setBlockContent(falseBlockContentList);

    return falseBlock;
  }
}
