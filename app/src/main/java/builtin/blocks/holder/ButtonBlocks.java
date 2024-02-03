package builtin.blocks.holder;

import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlockContent;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class ButtonBlocks {

  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#e74c3c");
    blocksHolder.setName("Button");
    blocksHolder.setTags(new String[] {Language.HTML});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getButtonBlock());

    blocksHolder.setBlocks(blocksList);
    return blocksHolder;
  }

  public static Block getButtonBlock() {
    Block buttonBlock = new Block();
    buttonBlock.setColor("#e74c3c");
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
}
