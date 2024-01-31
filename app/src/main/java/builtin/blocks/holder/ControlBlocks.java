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

public class ControlBlocks {
  public static BlocksHolder getBlockHolder() {
    BlocksHolder blocksHolder = new BlocksHolder();
    blocksHolder.setColor("#E1A92A");
    blocksHolder.setName("Control");
    blocksHolder.setTags(new String[] {Language.JavaScript});

    ArrayList<Block> blocksList = new ArrayList<Block>();

    blocksList.add(getIfBlock());
    blocksList.add(getIncrementalForLoopBlock());
    blocksList.add(getIncrementalForLoopBlockWithVariableValueProvider());
    blocksList.add(getDecrementalForLoopBlock());
    blocksList.add(getDecrementalForLoopBlockWithVariableValueProvider());

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

    BlockContent ifBlockContent3 = new BlockContent();
    ifBlockContent3.setText("then");
    ifBlockContentList.add(ifBlockContent3);

    ifBlock.setBlockContent(ifBlockContentList);

    return ifBlock;
  }

  public static ComplexBlock getIncrementalForLoopBlock() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = 0; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("++) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Language.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("++");
    forBlockContentList.add(forLoopBlockContent6);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }

  public static ComplexBlock getIncrementalForLoopBlockWithVariableValueProvider() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variableValue"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("++) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Language.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("value");
    forBlockContentList.add(forLoopBlockContent6);

    SourceContent forLoopBlockContent7 = new SourceContent();
    forLoopBlockContent7.setId("variableValue");
    forBlockContentList.add(forLoopBlockContent7);

    BlockContent forLoopBlockContent8 = new BlockContent();
    forLoopBlockContent8.setText("++");
    forBlockContentList.add(forLoopBlockContent8);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }

  public static ComplexBlock getDecrementalForLoopBlock() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = 0; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("--) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Language.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("--");
    forBlockContentList.add(forLoopBlockContent6);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }

  public static ComplexBlock getDecrementalForLoopBlockWithVariableValueProvider() {
    ComplexBlock forLoopBlock = new ComplexBlock();
    forLoopBlock.setColor("#E1A92A");
    forLoopBlock.setBlockType(Block.BlockType.complexBlock);
    forLoopBlock.setName("for-loop");

    StringBuilder ifBlockStringBuilder = new StringBuilder();
    ifBlockStringBuilder.append("for (let ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append(" = ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variableValue"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("condition"));
    ifBlockStringBuilder.append("; ");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("variable"));
    ifBlockStringBuilder.append("--) {\n");
    ifBlockStringBuilder.append("\t");
    ifBlockStringBuilder.append(CodeReplacer.getReplacer("complexBlockContent"));
    ifBlockStringBuilder.append("\n}");

    forLoopBlock.setRawCode(ifBlockStringBuilder.toString());

    forLoopBlock.setTags(new String[] {Language.JavaScript});

    ArrayList<BlockContent> forBlockContentList = new ArrayList<BlockContent>();

    BlockContent forLoopBlockContent1 = new BlockContent();
    forLoopBlockContent1.setText("for");
    forBlockContentList.add(forLoopBlockContent1);

    BlockContent forLoopBlockContent2 = new BlockContent();
    forLoopBlockContent2.setText("condition");
    forBlockContentList.add(forLoopBlockContent2);

    BooleanContent forLoopBlockContent3 = new BooleanContent();
    forLoopBlockContent3.setId("condition");
    forBlockContentList.add(forLoopBlockContent3);

    BlockContent forLoopBlockContent4 = new BlockContent();
    forLoopBlockContent4.setText("loop variable");
    forBlockContentList.add(forLoopBlockContent4);

    SourceContent forLoopBlockContent5 = new SourceContent();
    forLoopBlockContent5.setId("variable");
    forBlockContentList.add(forLoopBlockContent5);

    BlockContent forLoopBlockContent6 = new BlockContent();
    forLoopBlockContent6.setText("value");
    forBlockContentList.add(forLoopBlockContent6);

    SourceContent forLoopBlockContent7 = new SourceContent();
    forLoopBlockContent7.setId("variableValue");
    forBlockContentList.add(forLoopBlockContent7);

    BlockContent forLoopBlockContent8 = new BlockContent();
    forLoopBlockContent8.setText("--");
    forBlockContentList.add(forLoopBlockContent8);

    forLoopBlock.setBlockContent(forBlockContentList);

    return forLoopBlock;
  }
}
