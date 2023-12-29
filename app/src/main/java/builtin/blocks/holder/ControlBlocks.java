package builtin.blocks.holder;

import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.BlocksHolder;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.blockcontent.BooleanContent;
import com.dragon.ide.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class ControlBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#E1A92A");
    blocksHolder.setName("Control");
    blocksHolder.setTags(new String[] {Language.JavaScript});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getIfBlock());

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static ComplexBlock getIfBlock() {
    ComplexBlock ifBlock = new ComplexBlock();
    ifBlock.setColor("#E1A92A");
    ifBlock.setBlockType(Block.BlockType.complexBlock);
    ifBlock.setName("if");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("if (");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append(") {\n\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    ifBlock.setRawCode(ifBlockStringBuilder.toString());

    ifBlock.setTags(new String[] {Language.JavaScript});

    ArrayList<BlockContent> ifBlockContentList = new ArrayList<BlockContent>();

    BlockContent ifBlockBlockContent1 = new BlockContent();
    ifBlockBlockContent1.setText("if");
    ifBlockContentList.add(ifBlockBlockContent1);

    BooleanContent ifBlockBlockContent2 = new BooleanContent();
    ifBlockBlockContent2.setId("condition");
    ifBlockContentList.add(ifBlockBlockContent2);

    ifBlock.setBlockContent(ifBlockContentList);

    return ifBlock;
  }
}
