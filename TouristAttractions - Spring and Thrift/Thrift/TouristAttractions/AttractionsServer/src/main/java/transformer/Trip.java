/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package transformer;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trip implements org.apache.thrift.TBase<Trip, Trip._Fields>, java.io.Serializable, Cloneable, Comparable<Trip> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Trip");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField TOURIST_ATTRACTION_FIELD_DESC = new org.apache.thrift.protocol.TField("touristAttraction", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TRANSPORT_COMPANY_FIELD_DESC = new org.apache.thrift.protocol.TField("transportCompany", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField LEAVING_HOUR_FIELD_DESC = new org.apache.thrift.protocol.TField("leavingHour", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("price", org.apache.thrift.protocol.TType.DOUBLE, (short)5);
  private static final org.apache.thrift.protocol.TField NR_SEATS_FIELD_DESC = new org.apache.thrift.protocol.TField("nrSeats", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TripStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TripTupleSchemeFactory());
  }

  public int id; // required
  public String touristAttraction; // required
  public String transportCompany; // required
  public LocalTime leavingHour; // required
  public double price; // required
  public int nrSeats; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    TOURIST_ATTRACTION((short)2, "touristAttraction"),
    TRANSPORT_COMPANY((short)3, "transportCompany"),
    LEAVING_HOUR((short)4, "leavingHour"),
    PRICE((short)5, "price"),
    NR_SEATS((short)6, "nrSeats");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // TOURIST_ATTRACTION
          return TOURIST_ATTRACTION;
        case 3: // TRANSPORT_COMPANY
          return TRANSPORT_COMPANY;
        case 4: // LEAVING_HOUR
          return LEAVING_HOUR;
        case 5: // PRICE
          return PRICE;
        case 6: // NR_SEATS
          return NR_SEATS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __PRICE_ISSET_ID = 1;
  private static final int __NRSEATS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TOURIST_ATTRACTION, new org.apache.thrift.meta_data.FieldMetaData("touristAttraction", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TRANSPORT_COMPANY, new org.apache.thrift.meta_data.FieldMetaData("transportCompany", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LEAVING_HOUR, new org.apache.thrift.meta_data.FieldMetaData("leavingHour", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, LocalTime.class)));
    tmpMap.put(_Fields.PRICE, new org.apache.thrift.meta_data.FieldMetaData("price", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.NR_SEATS, new org.apache.thrift.meta_data.FieldMetaData("nrSeats", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Trip.class, metaDataMap);
  }

  public Trip() {
  }

  public Trip(
    int id,
    String touristAttraction,
    String transportCompany,
    LocalTime leavingHour,
    double price,
    int nrSeats)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.touristAttraction = touristAttraction;
    this.transportCompany = transportCompany;
    this.leavingHour = leavingHour;
    this.price = price;
    setPriceIsSet(true);
    this.nrSeats = nrSeats;
    setNrSeatsIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Trip(Trip other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetTouristAttraction()) {
      this.touristAttraction = other.touristAttraction;
    }
    if (other.isSetTransportCompany()) {
      this.transportCompany = other.transportCompany;
    }
    if (other.isSetLeavingHour()) {
      this.leavingHour = new LocalTime(other.leavingHour);
    }
    this.price = other.price;
    this.nrSeats = other.nrSeats;
  }

  public Trip deepCopy() {
    return new Trip(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.touristAttraction = null;
    this.transportCompany = null;
    this.leavingHour = null;
    setPriceIsSet(false);
    this.price = 0.0;
    setNrSeatsIsSet(false);
    this.nrSeats = 0;
  }

  public int getId() {
    return this.id;
  }

  public Trip setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public String getTouristAttraction() {
    return this.touristAttraction;
  }

  public Trip setTouristAttraction(String touristAttraction) {
    this.touristAttraction = touristAttraction;
    return this;
  }

  public void unsetTouristAttraction() {
    this.touristAttraction = null;
  }

  /** Returns true if field touristAttraction is set (has been assigned a value) and false otherwise */
  public boolean isSetTouristAttraction() {
    return this.touristAttraction != null;
  }

  public void setTouristAttractionIsSet(boolean value) {
    if (!value) {
      this.touristAttraction = null;
    }
  }

  public String getTransportCompany() {
    return this.transportCompany;
  }

  public Trip setTransportCompany(String transportCompany) {
    this.transportCompany = transportCompany;
    return this;
  }

  public void unsetTransportCompany() {
    this.transportCompany = null;
  }

  /** Returns true if field transportCompany is set (has been assigned a value) and false otherwise */
  public boolean isSetTransportCompany() {
    return this.transportCompany != null;
  }

  public void setTransportCompanyIsSet(boolean value) {
    if (!value) {
      this.transportCompany = null;
    }
  }

  public LocalTime getLeavingHour() {
    return this.leavingHour;
  }

  public Trip setLeavingHour(LocalTime leavingHour) {
    this.leavingHour = leavingHour;
    return this;
  }

  public void unsetLeavingHour() {
    this.leavingHour = null;
  }

  /** Returns true if field leavingHour is set (has been assigned a value) and false otherwise */
  public boolean isSetLeavingHour() {
    return this.leavingHour != null;
  }

  public void setLeavingHourIsSet(boolean value) {
    if (!value) {
      this.leavingHour = null;
    }
  }

  public double getPrice() {
    return this.price;
  }

  public Trip setPrice(double price) {
    this.price = price;
    setPriceIsSet(true);
    return this;
  }

  public void unsetPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  /** Returns true if field price is set (has been assigned a value) and false otherwise */
  public boolean isSetPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  public void setPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRICE_ISSET_ID, value);
  }

  public int getNrSeats() {
    return this.nrSeats;
  }

  public Trip setNrSeats(int nrSeats) {
    this.nrSeats = nrSeats;
    setNrSeatsIsSet(true);
    return this;
  }

  public void unsetNrSeats() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NRSEATS_ISSET_ID);
  }

  /** Returns true if field nrSeats is set (has been assigned a value) and false otherwise */
  public boolean isSetNrSeats() {
    return EncodingUtils.testBit(__isset_bitfield, __NRSEATS_ISSET_ID);
  }

  public void setNrSeatsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NRSEATS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case TOURIST_ATTRACTION:
      if (value == null) {
        unsetTouristAttraction();
      } else {
        setTouristAttraction((String)value);
      }
      break;

    case TRANSPORT_COMPANY:
      if (value == null) {
        unsetTransportCompany();
      } else {
        setTransportCompany((String)value);
      }
      break;

    case LEAVING_HOUR:
      if (value == null) {
        unsetLeavingHour();
      } else {
        setLeavingHour((LocalTime)value);
      }
      break;

    case PRICE:
      if (value == null) {
        unsetPrice();
      } else {
        setPrice((Double)value);
      }
      break;

    case NR_SEATS:
      if (value == null) {
        unsetNrSeats();
      } else {
        setNrSeats((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Integer.valueOf(getId());

    case TOURIST_ATTRACTION:
      return getTouristAttraction();

    case TRANSPORT_COMPANY:
      return getTransportCompany();

    case LEAVING_HOUR:
      return getLeavingHour();

    case PRICE:
      return Double.valueOf(getPrice());

    case NR_SEATS:
      return Integer.valueOf(getNrSeats());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case TOURIST_ATTRACTION:
      return isSetTouristAttraction();
    case TRANSPORT_COMPANY:
      return isSetTransportCompany();
    case LEAVING_HOUR:
      return isSetLeavingHour();
    case PRICE:
      return isSetPrice();
    case NR_SEATS:
      return isSetNrSeats();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Trip)
      return this.equals((Trip)that);
    return false;
  }

  public boolean equals(Trip that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_touristAttraction = true && this.isSetTouristAttraction();
    boolean that_present_touristAttraction = true && that.isSetTouristAttraction();
    if (this_present_touristAttraction || that_present_touristAttraction) {
      if (!(this_present_touristAttraction && that_present_touristAttraction))
        return false;
      if (!this.touristAttraction.equals(that.touristAttraction))
        return false;
    }

    boolean this_present_transportCompany = true && this.isSetTransportCompany();
    boolean that_present_transportCompany = true && that.isSetTransportCompany();
    if (this_present_transportCompany || that_present_transportCompany) {
      if (!(this_present_transportCompany && that_present_transportCompany))
        return false;
      if (!this.transportCompany.equals(that.transportCompany))
        return false;
    }

    boolean this_present_leavingHour = true && this.isSetLeavingHour();
    boolean that_present_leavingHour = true && that.isSetLeavingHour();
    if (this_present_leavingHour || that_present_leavingHour) {
      if (!(this_present_leavingHour && that_present_leavingHour))
        return false;
      if (!this.leavingHour.equals(that.leavingHour))
        return false;
    }

    boolean this_present_price = true;
    boolean that_present_price = true;
    if (this_present_price || that_present_price) {
      if (!(this_present_price && that_present_price))
        return false;
      if (this.price != that.price)
        return false;
    }

    boolean this_present_nrSeats = true;
    boolean that_present_nrSeats = true;
    if (this_present_nrSeats || that_present_nrSeats) {
      if (!(this_present_nrSeats && that_present_nrSeats))
        return false;
      if (this.nrSeats != that.nrSeats)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Trip other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTouristAttraction()).compareTo(other.isSetTouristAttraction());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTouristAttraction()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.touristAttraction, other.touristAttraction);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTransportCompany()).compareTo(other.isSetTransportCompany());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTransportCompany()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.transportCompany, other.transportCompany);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLeavingHour()).compareTo(other.isSetLeavingHour());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLeavingHour()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.leavingHour, other.leavingHour);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrice()).compareTo(other.isSetPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.price, other.price);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNrSeats()).compareTo(other.isSetNrSeats());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNrSeats()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nrSeats, other.nrSeats);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Trip(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("touristAttraction:");
    if (this.touristAttraction == null) {
      sb.append("null");
    } else {
      sb.append(this.touristAttraction);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("transportCompany:");
    if (this.transportCompany == null) {
      sb.append("null");
    } else {
      sb.append(this.transportCompany);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("leavingHour:");
    if (this.leavingHour == null) {
      sb.append("null");
    } else {
      sb.append(this.leavingHour);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("price:");
    sb.append(this.price);
    first = false;
    if (!first) sb.append(", ");
    sb.append("nrSeats:");
    sb.append(this.nrSeats);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (leavingHour != null) {
      leavingHour.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TripStandardSchemeFactory implements SchemeFactory {
    public TripStandardScheme getScheme() {
      return new TripStandardScheme();
    }
  }

  private static class TripStandardScheme extends StandardScheme<Trip> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Trip struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TOURIST_ATTRACTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.touristAttraction = iprot.readString();
              struct.setTouristAttractionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TRANSPORT_COMPANY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.transportCompany = iprot.readString();
              struct.setTransportCompanyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LEAVING_HOUR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.leavingHour = new LocalTime();
              struct.leavingHour.read(iprot);
              struct.setLeavingHourIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.price = iprot.readDouble();
              struct.setPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // NR_SEATS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.nrSeats = iprot.readI32();
              struct.setNrSeatsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Trip struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      if (struct.touristAttraction != null) {
        oprot.writeFieldBegin(TOURIST_ATTRACTION_FIELD_DESC);
        oprot.writeString(struct.touristAttraction);
        oprot.writeFieldEnd();
      }
      if (struct.transportCompany != null) {
        oprot.writeFieldBegin(TRANSPORT_COMPANY_FIELD_DESC);
        oprot.writeString(struct.transportCompany);
        oprot.writeFieldEnd();
      }
      if (struct.leavingHour != null) {
        oprot.writeFieldBegin(LEAVING_HOUR_FIELD_DESC);
        struct.leavingHour.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PRICE_FIELD_DESC);
      oprot.writeDouble(struct.price);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NR_SEATS_FIELD_DESC);
      oprot.writeI32(struct.nrSeats);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TripTupleSchemeFactory implements SchemeFactory {
    public TripTupleScheme getScheme() {
      return new TripTupleScheme();
    }
  }

  private static class TripTupleScheme extends TupleScheme<Trip> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Trip struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetTouristAttraction()) {
        optionals.set(1);
      }
      if (struct.isSetTransportCompany()) {
        optionals.set(2);
      }
      if (struct.isSetLeavingHour()) {
        optionals.set(3);
      }
      if (struct.isSetPrice()) {
        optionals.set(4);
      }
      if (struct.isSetNrSeats()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetTouristAttraction()) {
        oprot.writeString(struct.touristAttraction);
      }
      if (struct.isSetTransportCompany()) {
        oprot.writeString(struct.transportCompany);
      }
      if (struct.isSetLeavingHour()) {
        struct.leavingHour.write(oprot);
      }
      if (struct.isSetPrice()) {
        oprot.writeDouble(struct.price);
      }
      if (struct.isSetNrSeats()) {
        oprot.writeI32(struct.nrSeats);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Trip struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.touristAttraction = iprot.readString();
        struct.setTouristAttractionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.transportCompany = iprot.readString();
        struct.setTransportCompanyIsSet(true);
      }
      if (incoming.get(3)) {
        struct.leavingHour = new LocalTime();
        struct.leavingHour.read(iprot);
        struct.setLeavingHourIsSet(true);
      }
      if (incoming.get(4)) {
        struct.price = iprot.readDouble();
        struct.setPriceIsSet(true);
      }
      if (incoming.get(5)) {
        struct.nrSeats = iprot.readI32();
        struct.setNrSeatsIsSet(true);
      }
    }
  }

}
