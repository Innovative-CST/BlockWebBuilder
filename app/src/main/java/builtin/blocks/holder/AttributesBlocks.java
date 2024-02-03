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
    blocksHolder.setTags(new String[] {Language.HTML});

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
    idBlock.setTags(new String[] {Language.HTML});
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
    classBlock.setTags(new String[] {Language.HTML});
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
