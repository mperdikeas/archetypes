package gr.neuropublic.ab_jpa.facades;

import gr.neuropublic.base.IFacade;
import gr.neuropublic.ab_jpa.entities.*;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Query;

public interface IBFacade extends IFacade<B> {

    @Local
    public interface ILocal extends IBFacade {}

    @Remote
    public interface IRemote extends IBFacade {}

    public B initRow();

    public List<B> findAll();
    public int delAll();
    public B findById(Integer id);
    public int delById(Integer id);
    public List<B> findByB1(String b1);
    public int delByB1(String b1);
    public List<B> findByA(A a);
    public List<B> findByA(Integer a);
    public int delByA(A a);
    public int delByA(Integer a);
    public List<B> findB(String b1, A a, int recordLimit);

}
