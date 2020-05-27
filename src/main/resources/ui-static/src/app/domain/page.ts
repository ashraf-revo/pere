export class Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  last: boolean;
  number: number;
  size: number;
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}
