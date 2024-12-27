package com.app.journal.Service;

import com.app.journal.Entity.JournalEntry;
import com.app.journal.Entity.User;
import com.app.journal.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void createEntry(JournalEntry journalEntry, String username){
        try{
            User user=userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved= journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch(Exception e){
//            System.out.println(e);
            throw new RuntimeException("An error occured while creating Journal Entry");
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        try{
            boolean removed=false;
            User user=userService.findByUsername(username);
            removed= user.getJournalEntries().removeIf(x-> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        }catch(Exception e){
            throw  new RuntimeException("Error occured while deleting the Journal Entry",e);
        }

    }


}
  