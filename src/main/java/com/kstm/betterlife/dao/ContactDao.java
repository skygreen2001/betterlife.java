package com.kstm.betterlife.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kstm.betterlife.domain.Contact;

@Repository
public class ContactDao {
//	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	 @Resource
	 public void setSessionFactory(SessionFactory sessionFactory) {
		 this.sessionFactory = sessionFactory;
	 }

	protected Session getSession() {
		if (this.session == null) {
			this.session = this.sessionFactory.openSession();
		}
		return this.session;
	}

	/**
	 * 保存联系人信息
	 * 
	 * @param contact
	 *            联系人信息
	 * @return 是否保存或修改成功
	 */
	public Boolean save(Contact contact) {
		try {
//			Transaction tx = this.getSession().beginTransaction();
			this.getSession().save(contact);
//			tx.commit();
//			session.close();
//			this.session = null;
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * 修改联系人信息
	 * 
	 * @param contact
	 *            联系人信息
	 * @return 是否保存或修改成功
	 */
	public Boolean update(Contact contact) {
		try {
			Transaction tx = this.getSession().beginTransaction();
			this.getSession().clear();
			this.getSession().update(contact);
			tx.commit();
			session.close();
			this.session = null;
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 删除指定编号的联系人信息
	 * 
	 * @param id
	 *            联系人编号
	 * @return 是否删除成功
	 */
	public Boolean deleteContact(String id) {
		try {
			Transaction tx = this.getSession().beginTransaction();
			Integer contact_id = new Integer(id);
			Contact contact = (Contact) this.getSession().get(Contact.class, contact_id);
			this.getSession().delete(contact);
			tx.commit();
			session.close();
			this.session = null;
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 显示联系人
	 * 
	 * @param id
	 *            联系人编号
	 * @return 指定联系人编号的联系人信息
	 */
	public Contact viewContact(String id) 
	{
		return (Contact) this.getSession().createQuery("from Contact where contact_id=" + id).uniqueResult();
	}

	/**
	 * 列表联系人清单
	 * 
	 * @return 联系人列表
	 */
	@SuppressWarnings("unchecked")
	public List<Contact> listContacts() 
	{
		return (List<Contact>) this.getSession().createQuery("from Contact").list();
	}
	
	/**
	 * 联系人总数
	 * @return 总数
	 */
	public Long countContact()
	{
		return (Long) this.getSession().createQuery("select count(*) from Contact").uniqueResult();
	}
	
	
}
