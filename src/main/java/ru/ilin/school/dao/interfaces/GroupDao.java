package ru.ilin.school.dao.interfaces;

import ru.ilin.school.domain.Group;
import java.util.List;

public interface GroupDao extends CrudDao<Group,Long> {

    List<Group> getAllByMinQuantityStudent();
    Group findGroupByStudentId(int id);
}
