package frc.robot.constants;

import org.wpilib.fields.Field;
import org.wpilib.fields.Fields;

public class FieldConstants {

  /** Apriltag layout for 2026 REBUILT */
  public static final Field field = Field.loadField(Fields.FRC_2026_REBUILT_WELDED);

  /** Width of the field [meters] */
  public static final double FIELD_LENGTH = field.getFieldLength();

  /** Height of the field [meters] */
  public static final double FIELD_WIDTH = field.getFieldWidth();
}
