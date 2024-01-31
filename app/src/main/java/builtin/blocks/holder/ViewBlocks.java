package builtin.blocks.holder;

import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlockContent;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class ViewBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#4759B8");
    blocksHolder.setName("View");
    blocksHolder.setTags(new String[] {Language.HTML});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getButtonBlock());
        blocksList.add(getIdBlock());

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static Block getButtonBlock() {
    Block buttonBlock = new Block();
    buttonBlock.setColor("#4759B8");
    buttonBlock.setBlockType(Block.BlockType.defaultBlock);
    buttonBlock.setName("button");

    StringBuilder blockRawCode = new StringBuilder();
    blockRawCode.append("<button");
    blockRawCode.append(CodeReplacer.getReplacer("attachementBlock"));
    blockRawCode.append(">");
    blockRawCode.append(CodeReplacer.getReplacer("buttonText"));
    blockRawCode.append("</button>");

    buttonBlock.setRawCode(blockRawCode.toString());
    buttonBlock.setTags(new String[] {Language.HTML});
    buttonBlock.setEnableSideAttachableBlock(true);

    ArrayList<BlockContent> buttonBlockContentList = new ArrayList<BlockContent>();

    BlockContent buttonBlockContent1 = new BlockContent();
    buttonBlockContent1.setText("button");
    buttonBlockContentList.add(buttonBlockContent1);

    BlockContent buttonBlockContent2 = new BlockContent();
    buttonBlockContent2.setText("text");
    buttonBlockContentList.add(buttonBlockContent2);

    SourceContent buttonBlockContent3 = new SourceContent();
    buttonBlockContent3.setId("buttonText");
    buttonBlockContentList.add(buttonBlockContent3);

    buttonBlock.setBlockContent(buttonBlockContentList);

    return buttonBlock;
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
}
