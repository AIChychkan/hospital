import { Status } from "../enum/status.enum";

export interface Patient {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    birthDate: string;
    imageURL: string;
    diagnosis: string;
    treatment: string;
    status: Status; 
    doctor: string;



}