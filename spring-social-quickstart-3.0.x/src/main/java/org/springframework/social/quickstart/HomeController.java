/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.quickstart;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.vimeo.api.StreamingUploader;
import org.springframework.social.vimeo.api.Vimeo;
import org.springframework.social.vimeo.api.model.Videos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Simple little @Controller that invokes Facebook and renders the result.
 * The injected {@link Facebook} reference is configured with the required authorization credentials for the current user behind the scenes.
 *
 * @author Keith Donald
 */
@Controller
public class HomeController {

    private final static Log log = LogFactory.getLog(HomeController.class);
    private final Facebook facebook;
    private final Vimeo vimeo;

    @Inject
    public HomeController(Facebook facebook, Vimeo vimeo) {
        this.facebook = facebook;
        this.vimeo = vimeo;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestPart(value = "video") MultipartFile file, HttpServletResponse response, Model model) throws Exception {
        String mime = file.getContentType();
        File videoFile = File.createTempFile("video", "bin");
        IOUtils.copy(file.getInputStream(), new FileOutputStream(videoFile));
        if (!mime.startsWith("video")) {
            MagicMatch magicMatch = Magic.getMagicMatch(videoFile, true);
            mime = magicMatch.getMimeType();
        }
        StreamingUploader uploader = vimeo.uploadOperations().upload(mime, null);
        uploader.send(videoFile);
        return home(model);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        try {
            List<Reference> friends = facebook.friendOperations().getFriends();
            model.addAttribute("friends", friends);
        } catch (Exception e) {
            log.error("Error", e);
        }
        try {
            Videos videos = vimeo.videoOperations().likes(null, null, null, null);
            model.addAttribute("likes", videos.getVideos());
        } catch (Exception e) {
            log.error("Error", e);
        }
        return "home";
    }

}