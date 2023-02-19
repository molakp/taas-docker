import { User } from './../../utils/Models/user.model';

export interface Feedback {
    idFeedback?: string;
    comment?: string;
    user?: User;
    car?: Object[];    
}