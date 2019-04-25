package com.ldnr.punissement.ui.main.DAO;


import com.ldnr.punissement.ui.main.entity.IEntity;

import java.util.List;

public interface IDaoHelper {
    public void insert(IEntity object);

    public void update(IEntity object);

    public void delete(IEntity object);

    public List getList();

    public IEntity getElement(int i);

    public void deleteAllElelementsTable();


}
