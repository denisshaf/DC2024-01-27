package by.bsuir.dao.impl;

import by.bsuir.dao.EditorDao;
import by.bsuir.entities.Editor;
import by.bsuir.exceptions.DeleteException;
import by.bsuir.exceptions.UpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EditorDaoImpl implements EditorDao {

    private long counter = 0;
    private final Map<Long, Editor> map = new HashMap<>();

    @Override
    public Editor save(Editor entity) {
        counter++;
        map.put(counter, entity);
        entity.setId(counter);
        return entity;
    }

    @Override
    public void delete(long id) throws DeleteException {
        if (map.remove(id) == null) {
            throw new DeleteException("The editor has not been deleted", 40003L);
        }
    }

    @Override
    public List<Editor> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Optional<Editor> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Editor update(Editor updatedEditor) throws UpdateException {
        Long id = updatedEditor.getId();

        if (map.containsKey(id)) {
            Editor existingEditor = map.get(id);
            BeanUtils.copyProperties(updatedEditor, existingEditor);
            return existingEditor;
        } else {
            throw new UpdateException("Editor update failed", 40002L);
        }
    }
}
