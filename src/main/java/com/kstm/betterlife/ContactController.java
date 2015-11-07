package com.kstm.betterlife;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.kstm.betterlife.domain.Contact;
import com.kstm.betterlife.form.ContactForm;
import com.kstm.betterlife.service.ContactService;

@Controller
@SessionAttributes
public class ContactController {
	@Autowired
	private ContactService contactService;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@RequestMapping(value = "/editContact", method = RequestMethod.POST)
	public String editContact(@ModelAttribute("contact") ContactForm contactForm, BindingResult result) {
		Contact contact = new Contact();
		try {
			BeanUtils.copyProperties(contact, contactForm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("联系人名称:" + contact.getName() + "," + "联系方式:" + contact.getPhones());
		if (contact.getContact_id() > 0) {
			contactService.updateContact(contact);
		} else {
			contactService.saveContact(contact);
		}
		return "redirect:home";
	}

	@RequestMapping("/deleteContact")
	public String deleteContact(String id) {
		if (id != null) {
			Boolean isDeleteSuccess = contactService.deleteContact(id);
			if (!isDeleteSuccess)
				System.out.println("删除指定编号:" + id + "联系人失败！");
		}
		return "redirect:home";
	}

	@RequestMapping("/viewEditContact")
	public ModelAndView viewEditContact() {
		String id = request.getParameter("id");
		ContactForm contactForm = new ContactForm();
		if (id != null) {
			Contact contact = contactService.viewContact(id);
			try {
				BeanUtils.copyProperties(contactForm, contact);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("contact", "command", contactForm);
	}

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
}
