import java.util.Date;

import grails.orm.bootstrap.*
import grails.persistence.*

import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.h2.Driver

init = new HibernateDatastoreSpringInitializer([Person, Book, Author] as List)

//need to add the close dely to keep the table
def dataSource = new DriverManagerDataSource(Driver.name, "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1", 'sa', '')
//def dataSource = new DriverManagerDataSource(Driver.name, "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE", 'sa', '')
init.configureForDataSource(dataSource)
 

def will = new Person (name:'william').save()
println "Total people = " + Person.count()
 
 

@Entity
class Person {
		String name
		static constraints = {
				name blank:false
		}
}

@Entity
class Author {

		String name
		Date dateCreated
		Date lastUpdated
	   
		static hasMany = [books:Book]
	   
		static constraints = {
				name blank:false
				books nullable:true
		}
}

@Entity
class Book {

		String title
	   
		static belongsTo = [author:Author]
	   
		static constraints = {
				title blank:false
		}
}

def auth = new Author (name:"will").save ()
assert auth.validate()
 /*
auth.addToBooks (new Book (title:'mybook'))

println "authors" + Author.count()
println "with books" Authopr.get(1).books*.title
*/
