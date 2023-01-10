package com.example.activities.services;

import com.example.activities.model.Board;
import com.example.activities.model.User;
import com.example.activities.repositories.BoardRepository;
import com.example.activities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("boardService")
@Component
public class MemBoardService implements BoardService{

    @Autowired
    private BoardRepository boardrepo;

    List<Board> boards = new ArrayList<>();

    @Override
    @Transactional
    public void addBoard(Board b) {
        boardrepo.save(b);
    }

}
