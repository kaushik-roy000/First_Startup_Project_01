package com.learn.boost.exception;

public class NoteNotFoundException extends Exception
{
    public NoteNotFoundException(String noteId){
        super(noteId+ ": this note is not found");
    }
    public NoteNotFoundException(String noteId,Throwable cause){
        super(noteId+ ": this note is not found",cause);
    }
}
