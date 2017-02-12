package es.gorka.edu.repository;

import es.gorka.edu.dto.BookDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gorka.edu.assembler.Assembler;
import es.gorka.edu.connection.AbstractConnectionManager;
import es.gorka.edu.model.Author;
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
			 	preparedStatement.setString(2, book.getTitleOfBook());
			 	preparedStatement.setString(3, book.getNameOfAuthor());
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

	public ArrayList<Book> findBooks(Book book) {
		ArrayList<Book> list = new ArrayList<Book>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = conManager.open();
			preparedStatement = connection.prepareStatement("SELECT * FROM BookTable WHERE isbnOfBook = ? OR titleOfBook LIKE ? OR nameOfAuthor LIKE ?");
			preparedStatement.setString(1, "%" + book.getIsbn() + "%");
			preparedStatement.setString(2, "%" + book.getTitleOfBook() + "%");
			preparedStatement.setString(3, "%" + book.getNameOfAuthor() + "%");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Book bookTemp = new Book();
				bookTemp.setIsbn(resultSet.getString(1));
				bookTemp.setTitleOfBook(resultSet.getString(2));
				bookTemp.setNameOfAuthor(resultSet.getString(3));
				list.add(bookTemp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		}
		return list;
	}
}
