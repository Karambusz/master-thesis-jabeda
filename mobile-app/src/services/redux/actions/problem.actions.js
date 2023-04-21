import { SET_PROBLEM_PHOTO } from "../types/problem.types";

export const setProblemPhoto = photo => ({
    type: SET_PROBLEM_PHOTO,
    photo: photo
});