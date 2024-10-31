package fall24.hsf301.slot1.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import fall24.hsf301.slot1.pojo.Student;

public class HStudentDAO implements IStudentDAO {
	private SessionFactory sessionFactory = null;
	private Configuration cf = null;
	public HStudentDAO(String hibernateConfig) {
		cf = new Configuration().configure(hibernateConfig);
		sessionFactory = cf.buildSessionFactory();
	}
	public void save(Student student) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(student);
			t.commit();
			System.out.println("Successfully saved");
		}catch(Exception ex) {
			t.rollback();
			System.out.println("Error " + ex.getMessage());
		}finally {
			sessionFactory.close();
			session.close();
		}
	}
	public List<Student> findAll() {
	    Session session = sessionFactory.openSession();
	    List<Student> students = null;
	    try {
	        // Start the transaction
	        Transaction t = session.beginTransaction();
	        
	        // Query to get all students from the Student entity
	        students = session.createQuery("FROM Student", Student.class).list();
	        
	        // Commit the transaction
	        t.commit();
	        System.out.println("Successfully fetched students");
	    } catch (Exception ex) {
	        System.out.println("Error fetching students: " + ex.getMessage());
	    } finally {
	        session.close(); // Close the session
	    }
	    return students;
	}
	public void delete(int studentID) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		try {
			tx.begin();
			Student student = (Student) session.get(Student.class,studentID);
			session.delete(student);
			tx.commit();
		}catch(RuntimeException e) {
			tx.rollback();
			throw e;
		}finally {
			session.close();
		}
	}

	public Student findById(int studentID) {
		Session session = sessionFactory.openSession();
		try {
		return (Student) session.get(Student.class, studentID);
		}catch(RuntimeException e) {
			throw e;
		}finally {
			session.close();
		}
		}
	public void update(Student student) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(student);
			t.commit();
			System.out.println("Update saved");
		}catch(Exception ex) {
			t.rollback();
			System.out.println("Error " + ex.getMessage());
		}finally {
			sessionFactory.close();
			session.close();
		}
	}
	
}
