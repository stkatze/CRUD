package repository;

import exception.NotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {

    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();

    private final AtomicLong idPost = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        Post tempPost;
        if (post.getId() == 0) {
            idPost.incrementAndGet();
            tempPost = new Post(idPost.get(), post.getContent());
            posts.put(idPost.get(), tempPost);
        } else if (posts.containsKey(post.getId())) {
            tempPost = new Post(post.getId(), post.getContent());
            posts.replace(post.getId(), tempPost);
        } else {
            throw new NotFoundException("Не найден элемент с id - " + post.getId());
        }
        return tempPost;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("Не найден элемент с id - " + id);
        }
    }
}