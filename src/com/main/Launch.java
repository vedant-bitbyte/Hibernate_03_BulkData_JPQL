package com.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.entity.Employee;

public class Launch {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("config");
		EntityManager em = emf.createEntityManager();
		
		// In here we used the java persistence query language (JPQL), which means that we write the query with reference to
		// the entity
//		insert(em);
//		read(em);
//		update(em);
		delete(em);
		
		em.close();
		emf.close();
		
	}

	private static void delete(EntityManager em) {
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		String sql = "delete from Employee where eid >= :eid";
		
		Query query = em.createQuery(sql);
		query.setParameter("eid", 1237);
		
		query.executeUpdate();
		
		transaction.commit();
		System.out.println("Bulk data deleted...");
	}

	private static void update(EntityManager em) {
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		// here the esalary and eid are the Employee entity's parameters
		// Also here we used named parameters (:esalary, :eid) instead of positional parameters (?)
		String sql = "update Employee set esalary = :esalary where eid >= :eid";
		
		Query query = em.createQuery(sql);
		query.setParameter("esalary", 9888); // so here we mentioned the named parameter
		query.setParameter("eid", 1235);
		
		query.executeUpdate();
		
		transaction.commit();
		System.out.println("Bulk data updates...");
	}

	private static void read(EntityManager em) {
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		String sql = "select e from Employee e";
		// This is the JPQL, here we wrote the query using the entity class parameters, like the Employee is the class name
		// And compulsory we need to provide alias, here is e
		
		// now here we pass the Entity Employee, so the data is binded into the Employee obj
		// Here we used .createQuery()
		Query query = em.createQuery(sql, Employee.class);
		List<Employee> resultList = query.getResultList();
		
		for (Employee emp : resultList) {
			System.out.println(emp);
		}
		
		transaction.commit();
		
	}

	private static void insert(EntityManager em) {
		// Unlike JDBC that auto commits whenever a query is run, in Hibernate, we need to always begin a transaction and commit it
		// to make changes reflected in the DB
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		// So JPQL does not have a query to insert data, so we do it using the SQL native or JPA persist
		String sql = "insert into xemployee(id, name, address, salary) values (?,?,?,?);";
		
		// loop to represent a bulk insertion
		for (int i = 0; i < 10; i++) {
			
			// Here, we used the native sql which means we write the sql query with reference to the actual DB parameters,
			// like here we used the xemployee table with its actual parameters in the table
			Query nativeQuery = em.createNativeQuery(sql);
			
			nativeQuery.setParameter(1, 1234+i);
			nativeQuery.setParameter(2, "xyzzz");
			nativeQuery.setParameter(3, "afsgdfgdg");
			nativeQuery.setParameter(4, 1000*i);
			
			nativeQuery.executeUpdate();
			
		}
		
		transaction.commit();
		System.out.println("Bulk Data Inserted...");
	}

}
