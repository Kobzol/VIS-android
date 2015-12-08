package cz.kobzol.vis.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TestDTO extends IdentifiableDTO
{
    public String Name;
    public Date Date;
    public HashMap<Long, GradeDTO> Grades;
}