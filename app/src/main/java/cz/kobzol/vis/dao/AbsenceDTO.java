package cz.kobzol.vis.dao;

import java.util.Calendar;
import java.util.Date;

public class AbsenceDTO extends IdentifiableDTO
{
    public Date Date;
    public AbsenceTypeDTO Type;
    public boolean Excused;
    public long Student;
    public TeachingHourDTO Hour;
}
