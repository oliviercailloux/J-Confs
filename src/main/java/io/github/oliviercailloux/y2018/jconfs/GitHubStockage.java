package io.github.oliviercailloux.y2018.jconfs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FS;

public class GitHubStockage {

	public static void main(String args[]) {

		Path path = FileSystems.getDefault().getPath("./CalendarStockage/");
		if (FileKey.isGitRepository(new File(path.toString()+"/.git"), FS.DETECTED)) {
			System.out.println("Already cloned");
		} else {
			System.out.println("pas cloned");
			try {
				Git.cloneRepository().setURI("https://github.com/nikolaspaci/CalendarStockage.git")
						.setDirectory(new File(path.toString()))
						.call();
			} catch (GitAPIException e) {
				throw new IllegalStateException(e);
			}
		}
		
	}
}
