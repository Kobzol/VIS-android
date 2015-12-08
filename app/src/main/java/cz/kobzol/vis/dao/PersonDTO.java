package cz.kobzol.vis.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PersonDTO extends IdentifiableDTO
{
        public String Name;
        public String Surname;
        public Date BirthDate;
        public String Email;
        public PersonRoleDTO Role;
        public List<SubjectDTO> Subjects;
        public ClassDTO Class;
}
