package fall24.hsf301.slot1.repository;

import java.util.List;

import fall24.hsf301.slot1.dao.HStudentDAO;
import fall24.hsf301.slot1.dao.IStudentDAO;
import fall24.hsf301.slot1.dao.StudentDAO;
import fall24.hsf301.slot1.pojo.Student;

public class StudentRepository implements IStudentRepository {
        private IStudentDAO studentDAO;
		//private HStudentDAO studentDAO = null;
        public StudentRepository(String fileConfig,int choice){
            if(choice==1) {
            	studentDAO = new StudentDAO(fileConfig);
            }else {
            	studentDAO = new HStudentDAO(fileConfig);
            }
        	//studentDAO = new HStudentDAO(fileConfig);
        }
        @Override
        public List<Student> findAll() {
        // TODO Auto-generated method stub
        return studentDAO.findAll();
        }
        @Override
        public void save(Student student) {
            studentDAO.save(student);
        }
        @Override
        public void delete(int studentID) {
            studentDAO.delete(studentID);
        }
        @Override
        public Student findById(int studentID) {
            return studentDAO.findById(studentID);
        }
        @Override
        public void update(Student student) {
            studentDAO.update(student);
        }
}
