// ORM class for table 'iris_table'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Mon Oct 19 03:06:28 PDT 2015
// For connector: org.apache.sqoop.manager.MySQLManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import com.cloudera.sqoop.lib.JdbcWritableBridge;
import com.cloudera.sqoop.lib.DelimiterSet;
import com.cloudera.sqoop.lib.FieldFormatter;
import com.cloudera.sqoop.lib.RecordParser;
import com.cloudera.sqoop.lib.BooleanParser;
import com.cloudera.sqoop.lib.BlobRef;
import com.cloudera.sqoop.lib.ClobRef;
import com.cloudera.sqoop.lib.LargeObjectLoader;
import com.cloudera.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class iris_table extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  protected ResultSet __cur_result_set;
  private Integer id;
  public Integer get_id() {
    return id;
  }
  public void set_id(Integer id) {
    this.id = id;
  }
  public iris_table with_id(Integer id) {
    this.id = id;
    return this;
  }
  private Float sepalLength;
  public Float get_sepalLength() {
    return sepalLength;
  }
  public void set_sepalLength(Float sepalLength) {
    this.sepalLength = sepalLength;
  }
  public iris_table with_sepalLength(Float sepalLength) {
    this.sepalLength = sepalLength;
    return this;
  }
  private Float sepalWidth;
  public Float get_sepalWidth() {
    return sepalWidth;
  }
  public void set_sepalWidth(Float sepalWidth) {
    this.sepalWidth = sepalWidth;
  }
  public iris_table with_sepalWidth(Float sepalWidth) {
    this.sepalWidth = sepalWidth;
    return this;
  }
  private Float petalLength;
  public Float get_petalLength() {
    return petalLength;
  }
  public void set_petalLength(Float petalLength) {
    this.petalLength = petalLength;
  }
  public iris_table with_petalLength(Float petalLength) {
    this.petalLength = petalLength;
    return this;
  }
  private Float petalWidth;
  public Float get_petalWidth() {
    return petalWidth;
  }
  public void set_petalWidth(Float petalWidth) {
    this.petalWidth = petalWidth;
  }
  public iris_table with_petalWidth(Float petalWidth) {
    this.petalWidth = petalWidth;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof iris_table)) {
      return false;
    }
    iris_table that = (iris_table) o;
    boolean equal = true;
    equal = equal && (this.id == null ? that.id == null : this.id.equals(that.id));
    equal = equal && (this.sepalLength == null ? that.sepalLength == null : this.sepalLength.equals(that.sepalLength));
    equal = equal && (this.sepalWidth == null ? that.sepalWidth == null : this.sepalWidth.equals(that.sepalWidth));
    equal = equal && (this.petalLength == null ? that.petalLength == null : this.petalLength.equals(that.petalLength));
    equal = equal && (this.petalWidth == null ? that.petalWidth == null : this.petalWidth.equals(that.petalWidth));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof iris_table)) {
      return false;
    }
    iris_table that = (iris_table) o;
    boolean equal = true;
    equal = equal && (this.id == null ? that.id == null : this.id.equals(that.id));
    equal = equal && (this.sepalLength == null ? that.sepalLength == null : this.sepalLength.equals(that.sepalLength));
    equal = equal && (this.sepalWidth == null ? that.sepalWidth == null : this.sepalWidth.equals(that.sepalWidth));
    equal = equal && (this.petalLength == null ? that.petalLength == null : this.petalLength.equals(that.petalLength));
    equal = equal && (this.petalWidth == null ? that.petalWidth == null : this.petalWidth.equals(that.petalWidth));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.id = JdbcWritableBridge.readInteger(1, __dbResults);
    this.sepalLength = JdbcWritableBridge.readFloat(2, __dbResults);
    this.sepalWidth = JdbcWritableBridge.readFloat(3, __dbResults);
    this.petalLength = JdbcWritableBridge.readFloat(4, __dbResults);
    this.petalWidth = JdbcWritableBridge.readFloat(5, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.id = JdbcWritableBridge.readInteger(1, __dbResults);
    this.sepalLength = JdbcWritableBridge.readFloat(2, __dbResults);
    this.sepalWidth = JdbcWritableBridge.readFloat(3, __dbResults);
    this.petalLength = JdbcWritableBridge.readFloat(4, __dbResults);
    this.petalWidth = JdbcWritableBridge.readFloat(5, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(id, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeFloat(sepalLength, 2 + __off, 7, __dbStmt);
    JdbcWritableBridge.writeFloat(sepalWidth, 3 + __off, 7, __dbStmt);
    JdbcWritableBridge.writeFloat(petalLength, 4 + __off, 7, __dbStmt);
    JdbcWritableBridge.writeFloat(petalWidth, 5 + __off, 7, __dbStmt);
    return 5;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(id, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeFloat(sepalLength, 2 + __off, 7, __dbStmt);
    JdbcWritableBridge.writeFloat(sepalWidth, 3 + __off, 7, __dbStmt);
    JdbcWritableBridge.writeFloat(petalLength, 4 + __off, 7, __dbStmt);
    JdbcWritableBridge.writeFloat(petalWidth, 5 + __off, 7, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.id = null;
    } else {
    this.id = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.sepalLength = null;
    } else {
    this.sepalLength = Float.valueOf(__dataIn.readFloat());
    }
    if (__dataIn.readBoolean()) { 
        this.sepalWidth = null;
    } else {
    this.sepalWidth = Float.valueOf(__dataIn.readFloat());
    }
    if (__dataIn.readBoolean()) { 
        this.petalLength = null;
    } else {
    this.petalLength = Float.valueOf(__dataIn.readFloat());
    }
    if (__dataIn.readBoolean()) { 
        this.petalWidth = null;
    } else {
    this.petalWidth = Float.valueOf(__dataIn.readFloat());
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.id);
    }
    if (null == this.sepalLength) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.sepalLength);
    }
    if (null == this.sepalWidth) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.sepalWidth);
    }
    if (null == this.petalLength) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.petalLength);
    }
    if (null == this.petalWidth) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.petalWidth);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.id);
    }
    if (null == this.sepalLength) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.sepalLength);
    }
    if (null == this.sepalWidth) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.sepalWidth);
    }
    if (null == this.petalLength) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.petalLength);
    }
    if (null == this.petalWidth) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeFloat(this.petalWidth);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(id==null?"null":"" + id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(sepalLength==null?"null":"" + sepalLength, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(sepalWidth==null?"null":"" + sepalWidth, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(petalLength==null?"null":"" + petalLength, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(petalWidth==null?"null":"" + petalWidth, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(id==null?"null":"" + id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(sepalLength==null?"null":"" + sepalLength, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(sepalWidth==null?"null":"" + sepalWidth, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(petalLength==null?"null":"" + petalLength, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(petalWidth==null?"null":"" + petalWidth, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.id = null; } else {
      this.id = Integer.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.sepalLength = null; } else {
      this.sepalLength = Float.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.sepalWidth = null; } else {
      this.sepalWidth = Float.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.petalLength = null; } else {
      this.petalLength = Float.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.petalWidth = null; } else {
      this.petalWidth = Float.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.id = null; } else {
      this.id = Integer.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.sepalLength = null; } else {
      this.sepalLength = Float.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.sepalWidth = null; } else {
      this.sepalWidth = Float.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.petalLength = null; } else {
      this.petalLength = Float.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.petalWidth = null; } else {
      this.petalWidth = Float.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    iris_table o = (iris_table) super.clone();
    return o;
  }

  public void clone0(iris_table o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new TreeMap<String, Object>();
    __sqoop$field_map.put("id", this.id);
    __sqoop$field_map.put("sepalLength", this.sepalLength);
    __sqoop$field_map.put("sepalWidth", this.sepalWidth);
    __sqoop$field_map.put("petalLength", this.petalLength);
    __sqoop$field_map.put("petalWidth", this.petalWidth);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("id", this.id);
    __sqoop$field_map.put("sepalLength", this.sepalLength);
    __sqoop$field_map.put("sepalWidth", this.sepalWidth);
    __sqoop$field_map.put("petalLength", this.petalLength);
    __sqoop$field_map.put("petalWidth", this.petalWidth);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if ("id".equals(__fieldName)) {
      this.id = (Integer) __fieldVal;
    }
    else    if ("sepalLength".equals(__fieldName)) {
      this.sepalLength = (Float) __fieldVal;
    }
    else    if ("sepalWidth".equals(__fieldName)) {
      this.sepalWidth = (Float) __fieldVal;
    }
    else    if ("petalLength".equals(__fieldName)) {
      this.petalLength = (Float) __fieldVal;
    }
    else    if ("petalWidth".equals(__fieldName)) {
      this.petalWidth = (Float) __fieldVal;
    }
    else {
      throw new RuntimeException("No such field: " + __fieldName);
    }
  }
  public boolean setField0(String __fieldName, Object __fieldVal) {
    if ("id".equals(__fieldName)) {
      this.id = (Integer) __fieldVal;
      return true;
    }
    else    if ("sepalLength".equals(__fieldName)) {
      this.sepalLength = (Float) __fieldVal;
      return true;
    }
    else    if ("sepalWidth".equals(__fieldName)) {
      this.sepalWidth = (Float) __fieldVal;
      return true;
    }
    else    if ("petalLength".equals(__fieldName)) {
      this.petalLength = (Float) __fieldVal;
      return true;
    }
    else    if ("petalWidth".equals(__fieldName)) {
      this.petalWidth = (Float) __fieldVal;
      return true;
    }
    else {
      return false;    }
  }
}
