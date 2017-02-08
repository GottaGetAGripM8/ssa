package es.gorka.edu.repository;

import es.gorka.edu.dto.BookDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gorka.edu.assembler.Assembler;
import es.gorka.edu.connection.AbstractConnectionManager;

import es.gorka.edu.model.Book;

@Service
public class BookRepository {
	
	private static final Logger logger = LogManager.getLogger(BookRepository.class.getName());
	
	@Autowired
	private Assembler<BookDTO, Book> asesemblerBook;
	
	@Autowired
	private AbstractConnectionManager conManager;
	
	public void insert(BookDTO bookDTO) {
	
		 Connection connection = null;
		 PreparedStatement preparedStatement = null;
		 
		 Book book = new Book();
		 asesemblerBook.toEntity(bookDTO, book);
		 
		 try {
			 	connection = conManager.open();
			 	
			 	preparedStatement = connection.prepareStatement("INSERT INTO BookTable (isbnOfBook, titleOfBook, nameOfAuthor) VALUES (?, ?, ?)");
			 	
			 	preparedStatement.setString(1, book.getIsbn());
			 	preparedStatement.setString(2, book.getTitle());
			 	preparedStatement.setString(3, book.getNameAuthor());
			 	preparedStatement.executeUpdate();
		 
		 	logger.debug("INSERT INTO BookTable (isbnOfBook, titleOfBook, nameOfAuthor) VALUES (?, ?, ?)");
		 
		 	} catch (Exception e) {
		 			logger.error(e);
		 			throw new RuntimeException(e);
		 	} finally {
		 			conManager.close(preparedStatement);
		 			conManager.close(connection);
		 	}
	}
}
