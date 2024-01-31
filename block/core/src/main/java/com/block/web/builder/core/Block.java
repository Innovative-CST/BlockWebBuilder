package com.block.web.builder.core;

import com.block.web.builder.core.blockcontent.BooleanContent;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable, Cloneable {

  private static final long serialVersionUID = 428383837L;

  private String color;
  private String name;
  private String rawCode;
  private String returns;

  private String[] tags;

  private int blockType;

  private boolean enableSideAttachableBlock;

  private ArrayList<BlockContent> blockContent;
  private ArrayList<Block> sideAttachableBlock;

  public String getColor() {
    return color != null ? color : "#000000";
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getName() {
    return name != null ? name : "";
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRawCode() {
    return new String(rawCode);
  }

  public void setRawCode(String rawCode) {
    this.rawCode = rawCode;
  }

  public String getReturns() {
    return returns != null ? returns : "";
  }

  public void setReturns(String returns) {
    this.returns = returns;
  }

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public int getBlockType() {
    return blockType;
  }

  public void setBlockType(int blockType) {
    this.blockType = blockType;
  }

  public boolean getEnableSideAttachableBlock() {
    return enableSideAttachableBlock;
  }

  public void setEnableSideAttachableBlock(boolean enableSideAttachableBlock) {
    this.enableSideAttachableBlock = enableSideAttachableBlock;
  }

  public ArrayList<BlockContent> getBlockContent() {
    return blockContent;
  }

  public void setBlockContent(ArrayList<BlockContent> blockContent) {
    this.blockContent = blockContent;
  }

  public ArrayList<Block> getSideAttachableBlock() {
    return sideAttachableBlock;
  }

  public void setSideAttachableBlock(ArrayList<Block> sideAttachableBlock) {
    this.sideAttachableBlock = sideAttachableBlock;
  }

  public String getCode() {
    String blockRawCode = new String(getRawCode());
    for (int i = 0; i < getBlockContent().size(); ++i) {
      if (getBlockContent().get(i) instanceof ComplexBlockContent) {
        blockRawCode =
            blockRawCode.replace(
                CodeReplacer.getReplacer(((ComplexBlockContent) getBlockContent().get(i)).getId()),
                ((ComplexBlockContent) getBlockContent().get(i)).getCode());
      }
    }

    StringBuilder attachementBlocksCode = new StringBuilder();

    if (getEnableSideAttachableBlock()) {
      if (getBlockType() != BlockType.sideAttachableBlock) {
        for (int i = 0; i < getSideAttachableBlock().size(); ++i) {
          if (getSideAttachableBlock().get(i).getBlockType() == BlockType.sideAttachableBlock) {
            attachementBlocksCode.append(" ");
            attachementBlocksCode.append(getSideAttachableBlock().get(i).getCode());
          }
        }
      }
    }

    blockRawCode =
        blockRawCode.replace(
            CodeReplacer.getReplacer("attachementBlock"), attachementBlocksCode.toString());

    blockRawCode = CodeReplacer.removeBlockWebBuilderString(blockRawCode);
    return new String(blockRawCode);
  }

  /*
   * Clone all property of Block deeply
   */
  @Override
  public Block clone() throws CloneNotSupportedException {
    ArrayList<BlockContent> mBlockContent;
    ArrayList<Block> mSideAttachableBlock;

    if (getBlockContent() != null) {
      mBlockContent = new ArrayList<BlockContent>();
      for (int i = 0; i < getBlockContent().size(); ++i) {
        if (getBlockContent().get(i) instanceof ComplexBlockContent) {
          if (getBlockContent().get(i) instanceof SourceContent) {
            mBlockContent.add(((SourceContent) getBlockContent().get(i)).clone());
          } else if (getBlockContent().get(i) instanceof BooleanContent) {
            mBlockContent.add(((BooleanContent) getBlockContent().get(i)).clone());
          }
        } else if (getBlockContent().get(i) instanceof BlockContent) {
          mBlockContent.add(getBlockContent().get(i).clone());
        }
      }
    } else {
      mBlockContent = new ArrayList<BlockContent>();
    }

    if (getSideAttachableBlock() != null) {
      mSideAttachableBlock = new ArrayList<Block>();
      for (int i = 0; i < getSideAttachableBlock().size(); ++i) {
        if (getSideAttachableBlock().get(i) instanceof DoubleComplexBlock) {
          mSideAttachableBlock.add(((DoubleComplexBlock) getSideAttachableBlock().get(i)).clone());
        } else if (getSideAttachableBlock().get(i) instanceof ComplexBlock) {
          mSideAttachableBlock.add(((ComplexBlock) getSideAttachableBlock().get(i)).clone());
        } else if (getSideAttachableBlock().get(i) instanceof Block) {
          mSideAttachableBlock.add(getSideAttachableBlock().get(i).clone());
        }
      }
    } else {
      mSideAttachableBlock = new ArrayList<Block>();
    }

    Block mBlock = new Block();
    mBlock.setColor(getColor() != null ? new String(getColor()) : null);
    mBlock.setName(getName() != null ? new String(getName()) : null);
    mBlock.setBlockContent(mBlockContent);
    mBlock.setBlockType(new Integer(getBlockType()));
    mBlock.setRawCode(getRawCode() != null ? new String(getRawCode()) : null);
    mBlock.setReturns(getReturns() != null ? new String(getReturns()) : null);
    mBlock.setEnableSideAttachableBlock(new Boolean(getEnableSideAttachableBlock()));
    mBlock.setSideAttachableBlock(mSideAttachableBlock);
    return mBlock;
  }

  public final class BlockType {
    public static final int defaultBlock = 0;
    public static final int complexBlock = 1;
    public static final int doubleComplexBlock = 2;
    public static final int returnWithTypeBoolean = 3;
    public static final int sideAttachableBlock = 4;
  }
}
