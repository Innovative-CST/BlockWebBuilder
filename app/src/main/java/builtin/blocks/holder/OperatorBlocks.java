package builtin.blocks.holder;

import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.BlocksHolder;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.blockcontent.BooleanContent;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class OperatorBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#009900");
    blocksHolder.setName("Operator");
    blocksHolder.setTags(new String[] {Language.HTML, Language.CSS, Language.JavaScript});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getAddSourceBlock());

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static Block getAddSourceBlock() {
    Block addSource = new Block();
    addSource.setColor("#009900");
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
}
