package com.dragon.ide.objects;

import com.dragon.ide.objects.blockcontent.BooleanContent;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class DoubleComplexBlock extends ComplexBlock implements Serializable, Cloneable {
  public static final long serialVersionUID = 428383841L;
  private ArrayList<Block> doubleComplexBlocks;
  private ArrayList<BlockContent> complexBlockContent;

  public ArrayList<Block> getDoubleComplexBlocks() {
    if (doubleComplexBlocks != null) {
      return (ArrayList<Block>) this.doubleComplexBlocks.clone();
    }
    return new ArrayList<Block>();
  }

  public void setDoubleComplexBlocks(ArrayList<Block> doubleComplexBlocks) {
    this.doubleComplexBlocks = doubleComplexBlocks;
  }

  @Override
  public String getCode() {
    String blockRawCode = new String(getRawCode());
    for (int i = 0; i < getBlockContent().size(); ++i) {
      if (getBlockContent().get(i) instanceof ComplexBlockContent) {
        blockRawCode =
            blockRawCode.replaceAll(
                CodeReplacer.getReplacer(((ComplexBlockContent) getBlockContent().get(i)).getId()),
                ((ComplexBlockContent) getBlockContent().get(i)).getValue());
      }
    }
    StringBuilder blockListCode = new StringBuilder();
    for (int i = 0; i < getBlocks().size(); ++i) {
      if (i != 0) {
        blockListCode.append("\n");
      }
      if (getBlocks().get(i) instanceof DoubleComplexBlock) {
        blockListCode.append(((DoubleComplexBlock) getBlocks().get(i)).getCode());
      } else {
        if (getBlocks().get(i) instanceof ComplexBlock) {
          blockListCode.append(((ComplexBlock) getBlocks().get(i)).getCode());
        } else {
          if (getBlocks().get(i) instanceof Block) {
            blockListCode.append(getBlocks().get(i).getCode());
          }
        }
      }
    }
    String innerBlockCode = blockListCode.toString();
    blockRawCode =
        blockRawCode.replaceAll(CodeReplacer.getReplacer("complexBlockContent"), innerBlockCode);

    for (int i = 0; i < getComplexBlockContent().size(); ++i) {
      if (getComplexBlockContent().get(i) instanceof ComplexBlockContent) {
        blockRawCode =
            blockRawCode.replaceAll(
                CodeReplacer.getReplacer(((ComplexBlockContent) getBlockContent().get(i)).getId()),
                ((ComplexBlockContent) getBlockContent().get(i)).getCode());
      }
    }

    StringBuilder complexBlockListCode = new StringBuilder();
    for (int i = 0; i < getDoubleComplexBlocks().size(); ++i) {
      if (i != 0) {
        complexBlockListCode.append("\n");
      }
      if (getDoubleComplexBlocks().get(i) instanceof DoubleComplexBlock) {
        complexBlockListCode.append(((DoubleComplexBlock) getBlocks().get(i)).getCode());
      } else {
        if (getDoubleComplexBlocks().get(i) instanceof ComplexBlock) {
          complexBlockListCode.append(((ComplexBlock) getBlocks().get(i)).getCode());
        } else {
          if (getDoubleComplexBlocks().get(i) instanceof Block) {
            complexBlockListCode.append(getBlocks().get(i).getCode());
          }
        }
      }
    }
    String doubleInnerBlockCode = complexBlockListCode.toString();
    blockRawCode =
        blockRawCode.replaceAll(
            CodeReplacer.getReplacer("doubleComplexBlockContent"), doubleInnerBlockCode);
    blockRawCode = CodeReplacer.removeDragonIDEString(blockRawCode);
    return new String(blockRawCode);
  }

  public ArrayList<BlockContent> getComplexBlockContent() {
    return this.complexBlockContent;
  }

  public void setComplexBlockContent(ArrayList<BlockContent> conplexBlockContent) {
    this.complexBlockContent = complexBlockContent;
  }

  @Override
  public DoubleComplexBlock clone() throws CloneNotSupportedException {
    DoubleComplexBlock mDoubleComplexBlock = new DoubleComplexBlock();
    String mColor;
    if (getColor() != null) {
      mColor = new String(getColor());
    } else {
      mColor = new String("");
    }
    String mName;
    if (getName() != null) {
      mName = new String(getName());
    } else {
      mName = new String("");
    }
    ArrayList<BlockContent> mBlockContent;
    if (getBlockContent() != null) {
      mBlockContent = new ArrayList<BlockContent>();
      for (int i = 0; i < getBlockContent().size(); ++i) {
        if (getBlockContent().get(i) instanceof ComplexBlockContent) {
          if (getBlockContent().get(i) instanceof SourceContent) {
            mBlockContent.add(((SourceContent) getBlockContent().get(i)).clone());
          }
        } else if (getBlockContent().get(i) instanceof BlockContent) {
          mBlockContent.add(((BlockContent) getBlockContent().get(i)).clone());
        }
      }
    } else {
      mBlockContent = new ArrayList<BlockContent>();
    }
    int mBlockType;
    if (getBlockType() != 0) {
      mBlockType = new Integer(getBlockType());
    } else {
      mBlockType = 0;
    }
    String mRawCode;
    if (getRawCode() != null) {
      mRawCode = new String(getRawCode());
    } else {
      mRawCode = new String("");
    }
    String mReturns;
    if (getReturns() != null) {
      mReturns = new String(getReturns());
    } else {
      mReturns = new String("");
    }
    ArrayList<Block> mBlocks;
    if (getBlocks() != null) {
      mBlocks = new ArrayList<Block>();
      for (int i = 0; i < getBlocks().size(); ++i) {
        if (getBlocks().get(i) instanceof DoubleComplexBlock) {
          mBlocks.add(((DoubleComplexBlock) getBlocks().get(i)).clone());
        } else if (getBlocks().get(i) instanceof ComplexBlock) {
          mBlocks.add(((ComplexBlock) getBlocks().get(i)).clone());
        } else if (getBlocks().get(i) instanceof Block) {
          mBlocks.add(getBlocks().get(i).clone());
        }
      }
    } else {
      mBlocks = new ArrayList<Block>();
    }
    ArrayList<Block> mDoubleComplexBlocks;
    if (getDoubleComplexBlocks() != null) {
      mDoubleComplexBlocks = new ArrayList<Block>();
      for (int i = 0; i < getDoubleComplexBlocks().size(); ++i) {
        if (getDoubleComplexBlocks().get(i) instanceof DoubleComplexBlock) {
          mDoubleComplexBlocks.add(((DoubleComplexBlock) getDoubleComplexBlocks().get(i)).clone());
        } else if (getDoubleComplexBlocks().get(i) instanceof ComplexBlock) {
          mDoubleComplexBlocks.add(((ComplexBlock) getDoubleComplexBlocks().get(i)).clone());
        } else if (getDoubleComplexBlocks().get(i) instanceof Block) {
          mDoubleComplexBlocks.add(getDoubleComplexBlocks().get(i).clone());
        }
      }
    } else {
      mDoubleComplexBlocks = new ArrayList<Block>();
    }
    ArrayList<BlockContent> mComplexBlockContent;
    if (getComplexBlockContent() != null) {
      mComplexBlockContent = new ArrayList<BlockContent>();
      for (int i = 0; i < getComplexBlockContent().size(); ++i) {
        if (getComplexBlockContent().get(i) instanceof ComplexBlockContent) {
          if (getBlockContent().get(i) instanceof SourceContent) {
            mBlockContent.add(((SourceContent) getBlockContent().get(i)).clone());
          } else if (getBlockContent().get(i) instanceof BooleanContent) {
            mBlockContent.add(((BooleanContent) getBlockContent().get(i)).clone());
          }
        } else if (getBlockContent().get(i) instanceof BlockContent) {
          mComplexBlockContent.add(((BlockContent) getComplexBlockContent().get(i)).clone());
        }
      }
    } else {
      mComplexBlockContent = new ArrayList<BlockContent>();
    }
    ArrayList<Block> mSideAttachableBlock;
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
    boolean mEnableSideAttachableBlock = new Boolean(getEnableSideAttachableBlock());
    mDoubleComplexBlock.setColor(mColor);
    mDoubleComplexBlock.setName(mName);
    mDoubleComplexBlock.setBlockContent(mBlockContent);
    mDoubleComplexBlock.setBlockType(mBlockType);
    mDoubleComplexBlock.setRawCode(mRawCode);
    mDoubleComplexBlock.setReturns(mReturns);
    mDoubleComplexBlock.setBlocks(mBlocks);
    mDoubleComplexBlock.setDoubleComplexBlocks(mDoubleComplexBlocks);
    mDoubleComplexBlock.setComplexBlockContent(mComplexBlockContent);
    mDoubleComplexBlock.setEnableSideAttachableBlock(mEnableSideAttachableBlock);
    mDoubleComplexBlock.setSideAttachableBlock(mSideAttachableBlock);
    return mDoubleComplexBlock;
  }
}
