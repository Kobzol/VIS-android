package cz.kobzol.vis.dao;

import java.util.List;

public class SubjectDTO extends IdentifiableDTO
{
    public String Name;
    public int Year;
    public List<AbsenceDTO> Absences;
    public ScheduleDTO Schedule;
    public List<TestDTO> Tests;
}
