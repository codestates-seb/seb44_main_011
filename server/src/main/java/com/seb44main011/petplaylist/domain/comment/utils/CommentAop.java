//package com.seb44main011.petplaylist.domain.comment.utils;
//
//import com.seb44main011.petplaylist.domain.comment.service.CommentService;
//import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//
//@Slf4j
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class CommentAop {
//    private final MusicService musicService;
//    private final CommentService commentService;
//
//    @Pointcut("execution(* com.seb44main011.petplaylist.domain.comment.controller.CommentController.*(..))")
//    private void cutAll() {}
//
//    @Pointcut("execution(* com.seb44main011.petplaylist.domain.comment.controller.CommentController.patchComment(..))")
//    private void cutPatchMethod() {}
//
//    @Before("cutAll()")
//    public void beforeControllerMethod(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        if (args.length <= 0) {
//            log.info("no parameter");
//        } else {
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            String[] parameterNames = signature.getParameterNames();
//            for (int i = 0; i < args.length; i++) {
//                Object arg = args[i];
//                String parameterName = parameterNames[i];
//                log.info("Parameter: {} = {}", parameterName, arg);
//                if (parameterName.equals("musicId")) {
//                    long musicId = (long) arg;
//                    musicService.findMusic(musicId);
//                }
//                if (parameterName.equals("commentId")) {
//                    long commentId = (long) arg;
//                    commentService.findVerifiedComment(commentId);
//                }
//            }
//        }
//
//        for (Object arg : args) {
//            log.info("parameter type = {}", arg.getClass().getSimpleName());
//            log.info("parameter value = {}", arg);
//        }
//
////        log.info("Music Id = {}", musicId);
//    }
//
////    @Before("cutPatchMethod()")
////    public void beforePatchMethod(long commentId) {
////        commentService.findVerifiedComment(commentId);
////        log.info("Comment Id = {}", commentId);
////    }
//
////    @Before("cutDeleteMethod")
////    public void beforeDeleteMethod(long commentId) {
////        commentService.findVerifiedComment(commentId);
////        log.info("Comment Id = {}", commentId);
////    }
//}
