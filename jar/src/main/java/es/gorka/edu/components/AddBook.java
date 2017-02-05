package es.gorka.edu.components;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;

public class AddBook extends WebPage{
	
	public AddBook(){
		
		Form formBook = new Form("addingNewBook");
		
		formBook.add(new Label("nameBookLabel", getString("book.name")));
		formBook.add(new RequiredTextField("nameBook"));
		
		formBook.add(new Label("ISBNLabel", getString("isbn.identifier")));
		formBook.add(new RequiredTextField("ISBN"));
		
		formBook.add(new Label("nameAuthorLabel", getString("author.name")));
		formBook.add(new RequiredTextField("nameAuthor"));

		add(formBook);
	}
}
