package com.kstm.betterlife.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kstm.betterlife.dao.ContactDao;
import com.kstm.betterlife.domain.Contact;

@Service
public class ContactService {
	@Autowired
	private ContactDao contactDao;
	/**
	 * 列表联系人清单
	 * 
	 * @return 联系人列表
	 */
	public List<Contact> listContact()
	{
		return contactDao.listContacts();
	}
	
	/**
	 * 保存联系人信息
	 * 
	 * @param contact
	 *            联系人信息
	 * @return 是否保存或修改成功
	 */
	public Boolean saveContact(Contact contact){
		return contactDao.save(contact);
	}
	
	/**
	 * 修改联系人信息
	 * 
	 * @param contact
	 *            联系人信息
	 * @return 是否保存或修改成功
	 */
	public Boolean updateContact(Contact contact){
		return contactDao.update(contact);
	}
	
	/**
	 * 删除指定编号的联系人信息
	 * 
	 * @param id
	 *            联系人编号
	 * @return 是否删除成功
	 */
	public Boolean deleteContact(String id)
	{
		return contactDao.deleteContact(id);
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
		return contactDao.viewContact(id);
	}
	
	/**
	 * 联系人总数
	 * @return 总数
	 */
	public Long countContact()
	{
		return contactDao.countContact();
	}
}
