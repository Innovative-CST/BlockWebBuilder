package com.dragon.ide.objects;

import com.dragon.ide.objects.blockcontent.BooleanContent;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable, Cloneable {
  private static final long serialVersionUID = 428383837L;
  private String color;
  private String name;
  private ArrayList<BlockContent> blockContent;
  private int BlockType;
  private String rawCode;
  private String returns;

  public String getColor() {
    if (this.color != null) {
      return this.color;
    }
    return "#000000";
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getName() {
    if (this.name != null) {
      return this.name;
    }
    return "";
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBlockType() {
    return this.BlockType;
  }

  public void setBlockType(int BlockType) {
    this.BlockType = BlockType;
  }

  public String getCode() {
    String blockRawCode = new String(getRawCode());
    for (int i = 0; i < getBlockContent().size(); ++i) {
      if (getBlockContent().get(i) instanceof ComplexBlockContent) {
        blockRawCode =
            blockRawCode.replaceAll(
                CodeReplacer.getReplacer(((ComplexBlockContent) getBlockContent().get(i)).getId()),
                ((ComplexBlockContent) getBlockContent().get(i)).getCode());
      }
    }
    blockRawCode = CodeReplacer.removeDragonIDEString(blockRawCode);
    return new String(blockRawCode);
  }

  public String getRawCode() {
    return new String(this.rawCode);
  }

  public void setRawCode(String rawCode) {
    this.rawCode = rawCode;
  }

  public final class BlockType {
    public static final int defaultBlock = 0;
    public static final int complexBlock = 1;
    public static final int doubleComplexBlock = 2;
    public static final int returnWithTypeBoolean = 3;
  }

  public ArrayList<BlockContent> getBlockContent() {
    return blockContent;
  }

  public void setBlockContent(ArrayList<BlockContent> blockContent) {
    this.blockContent = blockContent;
  }

  public String getReturns() {
    if (returns != null) {
      return this.returns;
    }
    return new String("");
  }

  public void setReturns(String returns) {
    this.returns = returns;
  }

  @Override
  public Block clone() throws CloneNotSupportedException {
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
          } else if (getBlockContent().get(i) instanceof BooleanContent) {
            mBlockContent.add(((BooleanContent) getBlockContent().get(i)).clone());
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
    Block mBlock = new Block();
    mBlock.setColor(mColor);
    mBlock.setName(mName);
    mBlock.setBlockContent(mBlockContent);
    mBlock.setBlockType(mBlockType);
    mBlock.setRawCode(mRawCode);
    mBlock.setReturns(mReturns);
    return mBlock;
  }
}
